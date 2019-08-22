package collinearpts;

public class BruteCollinearPoints {


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
    }

    // the number of line segments
    public int numberOfSegments() {}

    // the line segments
    public LineSegment[] segments() {}
}
