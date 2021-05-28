import java.awt.Color;
import java.awt.*;
public class LongBlock extends Block {
    public LongBlock(){
        super(Color.yellow, new int[][][]{
                {
                        {
                                0, 0, 0, 0
                        },
                        {
                                1, 1, 1, 1
                        },
                        {
                                0, 0, 0, 0
                        },
                        {
                                0, 0, 0, 0
                        }
                },
                {
                        {
                                0, 0, 1, 0
                        },
                        {
                                0, 0, 1, 0
                        },
                        {
                                0, 0, 1, 0
                        },
                        {
                                0, 0, 1, 0
                        }
                },
                {
                        {
                                0, 0, 0, 0
                        },
                        {
                                0, 0, 0, 0
                        },
                        {
                                1, 1, 1, 1
                        },
                        {
                                0, 0, 0, 0
                        }
                },
                {
                        {
                                0, 1, 0, 0
                        },
                        {
                                0, 1, 0, 0
                        },
                        {
                                0, 1, 0, 0
                        },
                        {
                                0, 1, 0, 0
                        }
                }
        });
    }



}
