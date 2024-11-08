package store.view;

public enum Answer {
    YES("Y"),
    NO("N");

    private final String command;

    // 생성자
    Answer(String command) {
        this.command = command;
    }

    // Getter 메서드
    public String getCommand() {
        return command;
    }

    // Optional: 문자열로부터 해당 enum 값을 반환하는 메서드
    public static Answer fromCommand(String command) {
        for (Answer answer : Answer.values()) {
            if (answer.command.equalsIgnoreCase(command)) {
                return answer;
            }
        }
        throw new IllegalArgumentException("Invalid command: " + command);
    }

    public boolean isYes(){
        return this.equals(YES);
    }
}
