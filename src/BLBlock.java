import java.awt.Color;
public class BLBlock extends Block {
    public BLBlock(){
        super(Color.BLUE, new int[][][]{
                {
                        {
                                1, 0, 0
                        },
                        {
                                1, 1, 1
                        },
                        {
                                0, 0, 0
                        }
                },
                {
                        {
                                0, 1, 1
                        },
                        {
                                0, 1, 0
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
                                1, 1, 1
                        },
                        {
                                0, 0, 1
                        }
                },
                {
                        {
                                0, 1, 0
                        },
                        {
                                0, 1, 0
                        },
                        {
                                1, 1, 0
                        }
                }
        });
    }

}
