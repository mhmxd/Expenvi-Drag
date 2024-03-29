package experiment;

import graphic.MoRectangle;
import tools.Out;
import tools.Utils;

import java.awt.*;
import java.util.List;

import static java.lang.Math.*;
import static tools.Consts.*;
import static tools.Consts.STRINGS.SP;

public class BoxTrial extends Trial {
    private final String NAME = "BoxTrial/";

    public MoRectangle objectRect = new MoRectangle();
    public MoRectangle targetRect = new MoRectangle();

    // Factors
    private int fObjectWidth, fTargettWidth;
    private STRAIGHTNESS fStraightness;

    // Other values
    private DIRECTION dir;
    private int dist; // Corner-to-corner (diag) or edge-to-edge (straight)

    public BoxTrial(List<Integer> conf, int... params) {
        super(conf, params);

        if (conf == null || conf.size() < 3) {
            Out.d(NAME, "Config not properly set!");
            return;
        }

        //Set factors
        fObjectWidth = conf.get(0);
        fTargettWidth = conf.get(1);
        fStraightness = STRAIGHTNESS.get(conf.get(2));

        // Set params
        if (params != null && params.length > 0) {
            dist = Utils.mm2px(params[0]);
        }

        //-- Set secondary values
        objectRect.setSize(Utils.mm2px(fObjectWidth), Utils.mm2px(fObjectWidth));
        targetRect.setSize(Utils.mm2px(fTargettWidth), Utils.mm2px(fTargettWidth));

        dir = fStraightness.randDir(); // Random direction based on STRIAGHTNESS

        // Set the bound box size based on the direction
        switch (dir) {
            case N, S ->
                    boundRect.setSize(
                            targetRect.width,
                            objectRect.width + dist + targetRect.width);
            case E, W ->
                    boundRect.setSize(
                            objectRect.width + dist + targetRect.width,
                            targetRect.width);
            case NE, NW, SE, SW ->
                    boundRect.setSize(
                            (int) (objectRect.width + (dist / sqrt(2)) + targetRect.width),
                            (int) (objectRect.width + (dist / sqrt(2)) + targetRect.width));

        }
    }

    @Override
    public Point getEndPoint() {
        return targetRect.center;
    }

    @Override
    protected void positionElements() {
        final String TAG = NAME + "setElementsLocations";

        switch (dir) {
            case N -> {
                objectRect.setLocation(
                        (int) (boundRect.getCenterX() - objectRect.width / 2),
                        boundRect.bottomLeft.y - objectRect.width);
                targetRect.setLocation(boundRect.getLocation());
            }

            case S -> {
                objectRect.setLocation(
                        (int) (boundRect.getCenterX() - objectRect.width / 2),
                        boundRect.y);
                targetRect.setLocation(
                        boundRect.x,
                        boundRect.bottomLeft.y - targetRect.width);
            }

            case E -> {
                objectRect.setLocation(
                        boundRect.x,
                        (int) (boundRect.getCenterY() - objectRect.width / 2));
                targetRect.setLocation(
                        boundRect.topRight.x - targetRect.width,
                        boundRect.y);
            }

            case W -> {
                objectRect.setLocation(
                        boundRect.topRight.x - objectRect.width,
                        (int) (boundRect.getCenterY() - objectRect.width / 2));
                targetRect.setLocation(boundRect.getLocation());
            }

            case NE -> {
                objectRect.setLocation(
                        boundRect.x,
                        boundRect.bottomLeft.y - objectRect.width);
                targetRect.setLocation(
                        boundRect.topRight.x - targetRect.width,
                        boundRect.y);
            }

            case NW -> {
                objectRect.setLocation(
                        boundRect.bottomRight.x - objectRect.width,
                        boundRect.bottomRight.y - objectRect.width);
                targetRect.setLocation(boundRect.getLocation());
            }

            case SE -> {
                objectRect.setLocation(boundRect.getLocation());
                targetRect.setLocation(
                        boundRect.bottomRight.x - targetRect.width,
                        boundRect.bottomRight.y - targetRect.width);
            }

            case SW -> {
                objectRect.setLocation(
                        boundRect.topRight.x - objectRect.width,
                        boundRect.y);
                targetRect.setLocation(
                        boundRect.x,
                        boundRect.bottomLeft.y - targetRect.width);
            }
            
        }
    }


    @Override
    public String toLogString() {
        return boundRect.x + SP +
                boundRect.y + SP +
                fObjectWidth + SP +
                fTargettWidth + SP +
                dir.getAxis() + SP +
                dir;
    }

    @Override
    public String toString() {
        return toLogString();
    }
}
