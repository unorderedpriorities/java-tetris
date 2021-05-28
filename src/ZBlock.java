import java.awt.Color;
public class ZBlock extends Block {
    public ZBlock(){
        super(Color.RED, new int[][][]{
                {
                        {
                                1, 1, 0
                        },
                        {
                                0, 1, 1
                        },
                        {
                                0, 0, 0
                        }
                },
                {
                        {
                                0, 0, 1
                        },
                        {
                                0, 1, 1
                        },
                        {
                                0, 1, 0
                        }
                },
                {
                        {
                                0, 0, 0
                        },
                        {
                                1, 1, 0
                        },
                        {
                                0, 1, 1
                        }
                },
                {
                        {
                                0, 1, 0
                        },
                        {
                                1, 1, 0
                        },
                        {
                                1, 0, 0
                        }
                },
        });
    }

}
