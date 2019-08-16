import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    enum GridStatus {
        OPEN, BLOCKED
    }
    private GridStatus [][] grids;
    private WeightedQuickUnionUF uf;
    private int n;
    private int openCount;

    // returns the index of a grid in uf
    private int gridToUF(int row, int col) {
        // uf[0] is the top virtual site
        // uf[n * n + 1] is the bottom virtual site
        return (row - 1) * this.n + col;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.grids = new GridStatus[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grids[i][j] = GridStatus.BLOCKED;
            }
        }

        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }

        // open this site
        this.grids[row - 1][col - 1] = GridStatus.OPEN;
        this.openCount++;

        // connect adjacent open sites
        // above
        if (row > 1 && isOpen(row - 1, col)) {
            this.uf.union(gridToUF(row - 1, col), gridToUF(row, col));
        } else if (row == 1) {
            // connect to the top virtual site
            this.uf.union(0, gridToUF(row, col));
        }

        // below
        if (row < this.n && isOpen(row + 1, col)) {
            this.uf.union(gridToUF(row + 1, col), gridToUF(row, col));
        } else if (row == this.grids.length) {
            // connect to the bottom virtual site
            this.uf.union(this.n * this.n + 1, gridToUF(row, col));
        }

        // left
        if (col > 1 && isOpen(row, col - 1)) {
            this.uf.union(gridToUF(row, col - 1), gridToUF(row, col));
        }

        // right
        if (col < this.n && isOpen(row, col + 1)) {
            this.uf.union(gridToUF(row, col + 1), gridToUF(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }

        return this.grids[row - 1][col - 1] == GridStatus.OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }

        return isOpen(row, col) && this.uf.connected(0, gridToUF(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(0, this.n * this.n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {}
}