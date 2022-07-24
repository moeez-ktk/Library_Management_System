package MainCode;

public class Members {
    private final String name;
    private final int memberId;
    private int fine = 0, noOfBooks;
    private String password, securityQuestion, securityAnswer, dateJoined, contact, request;

    // ***** Getters *****

    public Members(String name, int id, String dateJoined, String contact) {
        this.name = name;
        this.memberId = id;
        this.dateJoined = dateJoined;
        this.contact = contact;
    }

    public Members(String name, int id, String dateJoined, String contact, String password, String securityQuestion,
            String securityAnswer, int fine, int noOfBooks, String request) {
        this.name = name;
        this.memberId = id;
        this.dateJoined = dateJoined;
        this.contact = contact;
        this.fine = fine;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.password = password;
        this.noOfBooks = noOfBooks;
        this.request = request;
    }

    public Members(String memberName, String contact, String password, String securityAnswer,
            String securityQuestion) {
        this.name = memberName;
        this.memberId = 0;
        this.contact = contact;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.securityQuestion = securityQuestion;
    }

    public String getName() {
        return this.name;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public String getDateJoined() {
        return this.dateJoined;
    }

    public String getContact() {
        return this.contact;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSecurityQuestion() {
        return this.securityQuestion;
    }

    public String getSecurityAnswer() {
        return this.securityAnswer;
    }

    public int getFine() {
        return this.fine;
    }

    public int getNoOfBooks() {
        return this.noOfBooks;
    }

    public String getRequest() {
        return this.request;
    }

    // ***** Setters *****

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(String question) {
        this.securityQuestion = question;
    }

    public void setSecurityAnswer(String answer) {
        this.securityAnswer = answer;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void addFine(int fine) {
        this.fine += fine;
    }
}
