package com.example.com.book_app2;

/**
 * The Book class object
 */
public class Book {
    private String Bookname;
    private String BooKauthor;
    private String Bookrating;
    private String Bookdiscription;
    private String BookpictureUrl;
    private String BookpictureUrlHD;


    /**
     *
     * @param Bookname name of the book
     * @param Bookauthor name of the author
     * @param Bookrating rating for the book
     * @param BooKdiscreption summary about the book
     * @param BookpictureUrl url for small image
     * @param BookpictureUrlHD url for lage image
     */
    public Book(String Bookname,String Bookauthor,String Bookrating,String BooKdiscreption,String BookpictureUrl, String BookpictureUrlHD){
        this.Bookname=Bookname;
        this.BooKauthor= Bookauthor;
        this.Bookrating=Bookrating;
        this.Bookdiscription=BooKdiscreption;
        this.BookpictureUrl= BookpictureUrl;
        this.BookpictureUrlHD=BookpictureUrlHD;
    }

    /**
     *
     * @return bookauthor
     */
    public String getBooKauthor() {
        return BooKauthor;
    }

    /**
     *
     * @return  Bookdiscription
     */
    public String getBookdiscription() {
        return Bookdiscription;
    }

    /**
     *
     * @return Bookname
     */
    public String getBookname() {
        return Bookname;
    }

    /**
     *
     * @return Bookrating
     */
    public String getBookrating() {
        return Bookrating;
    }

    /**
     *
     * @return BookpictureUrl
     */
    public String getBookpictureUrl() {
        return BookpictureUrl;
    }

    /**
     *
     * @return BookpictureUrlHD
     */
    public String getBookpictureUrlHD() {
        return BookpictureUrlHD;
    }
}
