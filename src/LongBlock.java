import java.awt.Color;
import java.awt.*;
public class LongBlock extends Block {
    public LongBlock(){
        super(new Color(0, 255, 255), new int[][][]{
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
