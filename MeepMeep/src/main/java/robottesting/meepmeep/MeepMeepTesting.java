package robottesting.meepmeep;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    // public static DATA FIELDS that persist between opmodes
    // These constant definitions can be copied into the MeepMeepTesting class to easily try out
    // various paths in meep meep

    //***********************************************************************
    // RED LEFT POSES -x -y, 90
    //***********************************************************************
    public static Pose2d RED_LEFT_START_POSE = new Pose2d(-35.25, -63.5, Math.toRadians(90));
    public static Pose2d RED_LEFT_PARK_LOCATION_1 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d RED_LEFT_PARK_LOCATION_2 = new Pose2d(-35.25, -35.25, Math.toRadians(270));
    public static Pose2d RED_LEFT_PARK_LOCATION_3 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d RED_LEFT_JUNCTION_POLE_LOCATION = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d RED_LEFT_SIGNAL_CONE_PICKUP_LOCATION = new Pose2d(0, 0, Math.toRadians(0));

    //***********************************************************************
    // RED RIGHT POSES +x -y 90
    //***********************************************************************
    public static Pose2d RED_RIGHT_START_POSE = new Pose2d(35.25, -63.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_PARK_LOCATION_1 = new Pose2d(11.75, -23.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_PARK_LOCATION_2 = new Pose2d(35.25, -23.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_PARK_LOCATION_3 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d RED_RIGHT_JUNCTION_POLE_LOCATION = new Pose2d(11.75, -23.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_SIGNAL_CONE_PICKUP_LOCATION = new Pose2d(0, 0, Math.toRadians(0));

    //***********************************************************************
    // BLUE LEFT POSES +x +y 270
    //***********************************************************************
    public static Pose2d BLUE_LEFT_START_POSE = new Pose2d(35.25, 63.5, Math.toRadians(270));
    public static Pose2d BLUE_LEFT_PARK_LOCATION_1 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d BLUE_LEFT_PARK_LOCATION_2 = new Pose2d(+35.25, +35.25, Math.toRadians(90));
    public static Pose2d BLUE_LEFT_PARK_LOCATION_3 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d BLUE_LEFT_JUNCTION_POLE_LOCATION = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d BLUE_LEFT_SIGNAL_CONE_PICKUP_LOCATION = new Pose2d(0, 0, Math.toRadians(0));

    //***********************************************************************
    // BLUE RIGHT POSES -x +y 270
    //***********************************************************************
    public static Pose2d BLUE_RIGHT_START_POSE = new Pose2d(-35.25, 63.5, Math.toRadians(270));
    public static Pose2d BLUE_RIGHT_PARK_LOCATION_1 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d BLUE_RIGHT_PARK_LOCATION_2 = new Pose2d(-35.25, +35.25, Math.toRadians(90));
    public static Pose2d BLUE_RIGHT_PARK_LOCATION_3 = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d BLUE_RIGHT_JUNCTION_POLE_LOCATION = new Pose2d(0, 0, Math.toRadians(0));
    public static Pose2d BLUE_RIGHT_SIGNAL_CONE_PICKUP_LOCATION = new Pose2d(0, 0, Math.toRadians(0));

    private static Pose2d startPose = RED_RIGHT_START_POSE;
    private static Pose2d junctionPolePose = RED_RIGHT_JUNCTION_POLE_LOCATION;
    private static Pose2d parkingLocationPose = RED_RIGHT_PARK_LOCATION_2;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(12.5, 14.2)

                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)
                                .splineTo(new Vector2d(11.75, -53), Math.toRadians(90))
                                .lineToLinearHeading(junctionPolePose)
                                //.lineToLinearHeading(parkingLocationPose)
                                .splineToConstantHeading(new Vector2d(23.5, -11.75), Math.toRadians(0))
                                .splineToConstantHeading(getVector2d(parkingLocationPose), Math.toRadians(270))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

    public static Vector2d getVector2d(Pose2d pose2d) {
        return new Vector2d(pose2d.getX(), pose2d.getY());
    }
}