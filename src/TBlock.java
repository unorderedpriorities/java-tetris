import java.awt.*;

public class TBlock extends Block{
    public TBlock() {
        super(new Color(138, 0, 139), new int[][][]{
                {
                        {
                            0, 0, 0
                        },
                        {
                            0, 1, 0
                        },
                        {
                            1, 1, 1
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
                            0, 1, 0
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
                            0, 1, 0
                        }
                }
        });
    }
}
