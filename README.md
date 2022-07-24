# Library_Management_System
GUI Application for Library Mangament System made using JavaFx
This App was made in collabartion with Haseeeb1 -> https://github.com/Haseeeb1

Basic Features:
1) Members Sign up Request. This request is forwarded to the admin. Account is only created if admin approves request.

2) Forgot Password. User can not change password but can view his password upon answering the security question set by the user during sign up process.

3) Number of books issued, total books, number of members etc are shown on dashboard.

4) Dynamic search using keyevent listener.

5) All data is imported from database and stored in an ObservableArrayList on login to avoid unnecessary opening and closing connection of databases.

6) Current time shown using simple animation.

Users Features:
1) Books TPage -> user can view all books and can request to issue book. User can only request one book at a time. If he tries to request another book he will be asked if he wants to overwrite the previous request with the current one. Issue date will be set using LocalDate.now(). Due date will be set by using LocalDate.now().plus(number of days).

2) Issue Page -> user will be able to see books issued by him. User will be able to return book.

3) On returning book fine will be calculated by comparing Due Date and return date. 

4) History Page-> user can view history of books issued by him. This page will show book name, issue date, return date and fine accumulated.

Admin Features:
1) Members Page -> admin can view all members. Admin has option to remove any member on basis of fine. Remove option will not work if fine is below 2000.

2) Books Page -> Admin can view all books.

3) Issued Books -> view all issued books.

4) Requests Page -> On this page admin can view requests of users to issue books and requests of account creation.

5) Add Book popup

6) Edit Book popup
