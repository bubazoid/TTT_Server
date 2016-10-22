import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bubnyshev on 21.10.2016.
 */
public class LogicTest {
    @Test
    public void lineOne(){
        Integer[] desk = {1,1,1,0,0,0,0,0,0};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void lineTwo(){
        Integer[] desk = {0,0,0,1,1,1,0,0,0};
        Assert.assertEquals(Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void lineThree(){
        Integer[] desk = {0,0,0,0,0,0,1,1,1};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void colOne(){
        Integer[] desk = {1,0,0,1,0,0,1,0,0};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void colTwo(){
        Integer[] desk = {0,1,0,0,1,0,0,1,0};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void colThree(){
        Integer[] desk = {0,0,1,0,0,1,0,0,1};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void diagonalOne(){
        Integer[] desk = {1,0,0,0,1,0,0,0,1};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void diagonalTwo(){
        Integer[] desk = {0,0,1,0,1,0,1,0,0};
        Assert.assertEquals(Logic.checkDesk(desk), Logic.WIN);
    }
    @Test
    public void normal(){
        Integer[] desk = {0,0,0,0,0,0,0,0,0};
        Assert.assertEquals(Logic.checkDesk(desk), Logic.NEXT);
    }

    @Test
    public void draw(){
        Integer[] desk = {1,2,1,1,2,1,2,1,2};
        Assert.assertEquals( Logic.checkDesk(desk), Logic.DRAW);
    }
}
