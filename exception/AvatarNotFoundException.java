public class AvatarNotFoundException extends RuntimeException {
    private long studentId;

    public AvatarNotFoundException(long studentId) {
        this.studentId = studentId;
    }

    public long getStudentId() {
        return studentId;
    }
}