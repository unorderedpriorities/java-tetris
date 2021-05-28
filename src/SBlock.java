import java.awt.Color;
public class SBlock extends Block {
    public SBlock(){
        super(Color.GREEN, new int[][][]{
                {
                        {
                                0, 1, 1
                        },
                        {
                                1, 1, 0
                        },
                        {
                                0, 0, 0
                        }
                },
                {
                        {
                                0, 1, 0
                        },
                        {
                                0, 1, 1
                        },
                        {
                                0, 0, 1
                        }
                },
                {
                        {
                                0, 0, 0
                        },
                        {
                                0, 1, 1
                        },
                        {
                                1, 1, 0
                        }
                },
                {
                        {
                                1, 0, 0
                        },
                        {
                                1, 1, 0
                        },
                        {
                                0, 1, 0
                        }
                }
        });
    }

}
