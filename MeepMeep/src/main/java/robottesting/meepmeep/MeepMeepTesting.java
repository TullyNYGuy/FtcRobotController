package robottesting.meepmeep;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleMecanumDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Arrays;

public class MeepMeepTesting {

    // public static DATA FIELDS that persist between opmodes
    // These constant definitions can be copied into the MeepMeepTesting class to easily try out
    // various paths in meep meep

    //***********************************************************************
    // LEFT POSES -x -y, 90
    //***********************************************************************
    public static Pose2d RED_LEFT_START_POSE = new Pose2d(-40.5, -63.5, Math.toRadians(90));
    public static Pose2d RED_LEFT_PARK_LOCATION_1 = new Pose2d(-11.75, -23.5, Math.toRadians(270));
    public static Pose2d RED_LEFT_PARK_LOCATION_2 = new Pose2d(-35.25, -11.75, Math.toRadians(270));
    public static Pose2d RED_LEFT_PARK_LOCATION_3 = new Pose2d(-56.75,-14.75 , Math.toRadians(270));

    public static double RED_LEFT_START_DUAL_SENSORS_BEFORE_POLE_DISTANCE = -5;
    public static double RED_LEFT_STOP_TRAJECTORY_AFTER_POLE_DISTANCE = 5;
    // For friday night testing
    //public static Pose2d RED_LEFT_START_DUAL_SENSORS_LOCATION = new Pose2d(-10.75, -23.5 + RED_LEFT_START_DUAL_SENSORS_BEFORE_POLE_DISTANCE, Math.toRadians(267.5));
    //public static Pose2d RED_LEFT_STOP_JUNCTION_POLE_TRAJECTORY_LOCATION = new Pose2d(-10.75, -23.5 + RED_LEFT_STOP_TRAJECTORY_AFTER_POLE_DISTANCE, Math.toRadians(267.5));
    // updated to use the power play drive that should have been used
    public static Pose2d RED_LEFT_START_DUAL_SENSORS_LOCATION = new Pose2d(-11.75, -22.0 + RED_LEFT_START_DUAL_SENSORS_BEFORE_POLE_DISTANCE, Math.toRadians(267.5));
    public static Pose2d RED_LEFT_STOP_JUNCTION_POLE_TRAJECTORY_LOCATION = new Pose2d(-11.75, -22.0 + RED_LEFT_STOP_TRAJECTORY_AFTER_POLE_DISTANCE, Math.toRadians(267.5));

    // in theory
    //public static Pose2d RED_LEFT_JUNCTION_POLE_LOCATION = new Pose2d(-11.75, -23.5, Math.toRadians(270));
    // was
    // public static Pose2d RED_LEFT_JUNCTION_POLE_LOCATION = new Pose2d(-14.75, -22.0, Math.toRadians(270));
    public static Pose2d RED_LEFT_JUNCTION_POLE_LOCATION = new Pose2d(-11.75, -22.0, Math.toRadians(270));
    public static Pose2d RED_LEFT_SIGNAL_CONE_PICKUP_LOCATION = new Pose2d(0, 0, Math.toRadians(0));
    //***********************************************************************
    // RIGHT POSES +x -y 90
    //***********************************************************************
    public static Pose2d RED_RIGHT_START_POSE = new Pose2d(30, -63.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_PARK_LOCATION_1 = new Pose2d(11.75, -23.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_PARK_LOCATION_2 = new Pose2d(35.25, -23.5, Math.toRadians(90));
    //public static Pose2d RED_RIGHT_PARK_LOCATION_3 = new Pose2d(58.75, -11.75, Math.toRadians(90));
    public static Pose2d RED_RIGHT_PARK_LOCATION_3 = new Pose2d(55.75, -11.75, Math.toRadians(85));

    public static double RED_RIGHT_START_DUAL_SENSORS_BEFORE_POLE_DISTANCE = -7;
    public static double RED_RIGHT_STOP_TRAJECTORY_AFTER_POLE_DISTANCE = 5;
    public static Pose2d RED_RIGHT_START_DUAL_SENSORS_LOCATION = new Pose2d(10.75, -25.5 + RED_RIGHT_START_DUAL_SENSORS_BEFORE_POLE_DISTANCE, Math.toRadians(90));
    public static Pose2d RED_RIGHT_STOP_JUNCTION_POLE_TRAJECTORY_LOCATION = new Pose2d(10.75, -25.5 + RED_RIGHT_STOP_TRAJECTORY_AFTER_POLE_DISTANCE, Math.toRadians(90));

    // in theory
    //public static Pose2d RED_RIGHT_JUNCTION_POLE_LOCATION = new Pose2d(11.75, -23.5, Math.toRadians(90));
    // for friday night testing
    //public static Pose2d RED_RIGHT_JUNCTION_POLE_LOCATION = new Pose2d(10.75, -25.5, Math.toRadians(90));
    // updated to power play drive that should have been used
    public static Pose2d RED_RIGHT_JUNCTION_POLE_LOCATION = new Pose2d(11.75, -23.5, Math.toRadians(90));
    public static Pose2d RED_RIGHT_SIGNAL_CONE_PICKUP_LOCATION = new Pose2d(0, 0, Math.toRadians(0));



    private static Pose2d startPose = RED_LEFT_START_POSE;
    private static Pose2d junctionPolePose = RED_LEFT_JUNCTION_POLE_LOCATION;
    private static Pose2d parkingLocationPose = RED_LEFT_PARK_LOCATION_3;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        MinVelocityConstraint velConstraintSlow = new MinVelocityConstraint(Arrays.asList(
                new AngularVelocityConstraint(Math.toRadians(144)),
                new MecanumVelocityConstraint(3, 10.625)
        ));
        ProfileAccelerationConstraint accelConstraint = new ProfileAccelerationConstraint(30);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(12.5, 14.2)

                .followTrajectorySequence(drive ->

//                        // for right side start to junction pole
//                        drive.trajectorySequenceBuilder(startPose)
//                                .splineTo(new Vector2d(11.75, -53), Math.toRadians(90))
//                                .splineToSplineHeading(RED_RIGHT_START_DUAL_SENSORS_LOCATION,Math.toRadians(90))
//                                .splineToSplineHeading(RED_RIGHT_STOP_JUNCTION_POLE_TRAJECTORY_LOCATION,Math.toRadians(90),
//                                        velConstraintSlow, // slower than normal speed
//                                        accelConstraint)
//                                .build()
//                );

                                // for right junction pole to parking
                                // vary the start location to simulate different scoring positions
                                drive.trajectorySequenceBuilder(new Pose2d(11.75, -22.0, Math.toRadians(90)))
                                        // end tangent forms a nice curve
                                        .splineToConstantHeading(new Vector2d(23.5, -10.75), Math.toRadians(0))
                                        // end tangent forms a nice curve
                                        .splineToConstantHeading(getVector2d(RED_RIGHT_PARK_LOCATION_2), Math.toRadians(270))
                                        .build()
                );

//                        // for left side start to junction pole
//                        drive.trajectorySequenceBuilder(startPose)
//                                .splineTo(new Vector2d(-13.75, -51), Math.toRadians(90))
//                                .splineToSplineHeading(RED_LEFT_START_DUAL_SENSORS_LOCATION,Math.toRadians(90))
//                                .splineToSplineHeading(RED_LEFT_STOP_JUNCTION_POLE_TRAJECTORY_LOCATION,Math.toRadians(90),
//                                        velConstraintSlow, // slower than normal speed
//                                        accelConstraint)
//                                .build()
//                );

//                                // for parking on left side
//                                // tweak the start postion to see what variations in robot location during score do
//                                drive.trajectorySequenceBuilder(new Pose2d(-11.75, -23.5, Math.toRadians(270)))
//                                        .lineTo(new Vector2d(-11.75, -20.5))
//                                        .splineToConstantHeading(new Vector2d(-23.5, -10.75), Math.toRadians(180))
//                                        .splineToSplineHeading((RED_LEFT_PARK_LOCATION_2), Math.toRadians(180))
//                                        .build()
//                );

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