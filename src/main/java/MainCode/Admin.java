package MainCode;

public class Admin {
    private final String name;
    private String password, securityQuestion, securityAnswer;

    public Admin(String name, String password, String securityQuestion, String securityAnswer) {
        this.name = name;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    // ***** Getters *****

    public String getPassword() {
        return this.password;
    }

    public String getSecurityQuestion() {
        return this.securityQuestion;
    }

    public String getSecurityAnswer() {
        return this.securityAnswer;
    }

    public String getName() {
        return this.name;
    }

    // ***** Setters *****

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(String question) {
        this.securityQuestion = question;
    }

    public void setSecurityAnswer(String answer) {
        this.securityAnswer = answer;
    }
}
