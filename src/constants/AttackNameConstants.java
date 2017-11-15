package constants;

public final class AttackNameConstants {
   public static final String[]LOW_RATE_ATTACKS      = new String[]{"slowBody", "slowHeaders", "slowRead"};
   public static final String[]HIGH_RATE_ATTACKS     = new String[]{"back", "land", "neptune", "pod", "smurf", "teardrop"};
   public static final String[]HIGH_RATE_AND_NORMAL  = new String[]{"normal","back", "land", "neptune", "pod", "smurf", "teardrop"};

   private AttackNameConstants(){}
}
