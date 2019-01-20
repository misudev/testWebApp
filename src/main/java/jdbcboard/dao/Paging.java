package jdbcboard.dao;

public class Paging {
    private final static int BLOCK_SIZE = 10;
    private final static int SIZE = 5;
    private int blockStartNum = 0;
    private int blockLastNum = 0;
    private int lastPageNum = 0;

    public Paging(long boardCount){
        lastPageNum = (int)boardCount/SIZE + ((boardCount%SIZE) == 0 ? 0 : 1);
    }

    public int getBlockStartNum() {
        return blockStartNum;
    }

    public void setBlockStartNum(int blockStartNum) {
        this.blockStartNum = blockStartNum;
    }

    public int getBlockLastNum() {
        return blockLastNum;
    }

    public void setBlockLastNum(int blockLastNum) {
        this.blockLastNum = blockLastNum;
    }

    public int getLastPageNum() {
        return lastPageNum;
    }

    public void setLastPageNum(int lastPageNum) {
        this.lastPageNum = lastPageNum;
    }

    public void makeBlock(int now){

        blockStartNum = ((now + 1)/BLOCK_SIZE) * BLOCK_SIZE + 1;
        blockLastNum = (now + BLOCK_SIZE - 1) < lastPageNum ? now + BLOCK_SIZE - 1 : lastPageNum;

    }


    @Override
    public String toString() {
        return "Paging{" +
                "blockStartNum=" + blockStartNum +
                ", blockLastNum=" + blockLastNum +
                ", lastPageNum=" + lastPageNum +
                '}';
    }

    /*
    public void makeLastPageNum(long boardCount){
        lastPageNum = (int)boardCount/SIZE + ((boardCount%SIZE) == 0 ? 0 : 1);

    }
    */
}
