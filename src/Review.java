public class Review {
    private String user;
    private int rating;
    private String comment;

    public Review() {}

    // Getters and Setters

    public String getUser() {
        return user != null ? user : "Несуществует";
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment != null ? comment : "Несуществует";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("User: %s, Rating: %d, Comment: %s", getUser(), getRating(), getComment());
    }
}
