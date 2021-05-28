import java.awt.Color;
public class LBlock extends Block {
    public LBlock(){
        super(Color.ORANGE, new int[][][]{
                {
                        {
                                0, 0, 1
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
                                0, 1, 0
                        },
                        {
                                0, 1, 0
                        },
                        {
                                0, 1, 1
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
                                1, 0, 0
                        }
                },
                {
                        {
                                1, 1, 0
                        },
                        {
                                0, 1, 0
                        },
                        {
                                0, 1, 0
                        }
                },
        });
    }

}