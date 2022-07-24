package MainCode;

public class Books {
    private String bookName, author, category, type;
    private final int bookId;
    private final String dateAdded;
    private String issuedBy;
    private String issuedDate;
    private String returnDate;
    private String dueDate;
    private String requestedBy;

    public Books(int id, String bookName, String author, String requestedBy) {
        this.bookName = bookName;
        this.author = author;
        this.bookId = id;
        this.dateAdded = "";
        this.requestedBy = requestedBy;
    }

    public Books(String bookName, String author, int id, String category, String issueDate, String returnDate,
            String issuedBy) {
        this.bookName = bookName;
        this.author = author;
        this.bookId = id;
        this.dateAdded = "";
        this.category = category;
        this.issuedBy = issuedBy;
        this.returnDate = returnDate;
    }

    public Books(String bookName, String author, int id, String category, String type, String dateAdded,
            String issuedBy, String returnDate, String issueDate, String dueDate, String requestedBy) {
        this.bookName = bookName;
        this.author = author;
        this.bookId = id;
        this.dateAdded = dateAdded;
        this.category = category;
        this.type = type;
        this.issuedBy = issuedBy;
        this.returnDate = returnDate;
        this.issuedDate = issueDate;
        this.dueDate = dueDate;
        this.requestedBy = requestedBy;
    }

    // ****** Setters ******

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public void setIssuedDate(String issuedate) {
        this.issuedDate = issuedate;
    }

    public void setReturndDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setdueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    // ****** Getters ******

    public String getBookName() {
        return this.bookName;
    }

    public String getCategory() {
        return this.category;
    }

    public String getType() {
        return this.type;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIssuedBy() {
        return this.issuedBy;
    }

    public int getBookId() {
        return this.bookId;
    }

    public String getDateAdded() {
        return this.dateAdded;
    }

    public String getIssuedDate() {
        return this.issuedDate;
    }

    public String getReturnDate() {
        return this.returnDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public String getRequestedBy() {
        return this.requestedBy;
    }
}