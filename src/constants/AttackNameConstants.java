package constants;

public enum AttackNameConstants {
   LOW_RATE_ATTACKS     ("slowBody", "slowHeaders", "slowRead"),
   HIGH_RATE_ATTACKS    ("back", "land", "neptune", "pod", "smurf", "teardrop"),
   HIGH_RATE_AND_NORMAL ("normal","back", "land", "neptune", "pod", "smurf", "teardrop");

   private final String[] values;

   private AttackNameConstants(String... values) {
      this.values = values;
   }

   public String[] getValues() {
      return values;
   }
}
