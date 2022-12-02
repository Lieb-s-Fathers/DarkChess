package model;

public enum ErrorType {
    NOError(10, "", "No Error"),
    ONE00(0, "Error 100", "File Not Found!"),
    ONE01(1, "Error 101", "文件格式错误"),
    ONE02(2, "Error 102", "棋盘错误"),
    ONE03(3, "Error 103", "棋子错误"),
    ONE04(4, "Error 104", "缺少行棋方"),
    ONE05(5,"105","行棋步骤错误");

    private final int num;
    private final String name;
    private final String message;

    ErrorType(int num, String name, String message) {
        this.num = num;
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
