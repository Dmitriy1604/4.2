package org.example.service;
@Service
@Transactional
public class AvatarService {
    private final int avatarFileSizeLimit = 300; // Kb
    @Value("${students.avatar.dir.path}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        logger.debug("Calling constructor AvatarService");
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("Calling method uploadAvatar (studentId = {})", studentId);

        if (file.getSize() > 1024 * avatarFileSizeLimit) {
            throw new FileIsTooBigException(avatarFileSizeLimit);
        }

        Student student = studentService.findStudent(studentId);

        if (student == null) {
            throw new StudentNotFoundException(studentId);
        }

        // Save avatar file to local disk
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
        }

        // Save avatar preview (smaller copy) to database
        Avatar avatar = findStudentAvatar(studentId);
        if (avatar == null) {
            avatar = new Avatar();
        }
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    public Avatar findStudentAvatar(Long studentId) {
        logger.debug("Calling method findStudentAvatar (studentId = {})", studentId);
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(null);
        if (avatar == null) {
            throw new AvatarNotFoundException(studentId);
        }
        return avatar;
    }

    public ResponseEntity<Collection<Avatar>> findByPagination(int page, int size) {
        logger.debug("Calling method findByPagination (page = {}, size = {})", page, size);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Collection<Avatar> avatars = avatarRepository.findAll(pageRequest).getContent();
        if (avatars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avatars);
    }

    // Generate smaller copy (100 x 100 pixels) of the avatar file.
    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}