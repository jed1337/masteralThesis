package constants;

public enum SpecificAttackTypeEnum {
   NORMAL("normal"),
   TCP_FLOOD("tcpFlood"),
   UDP_FLOOD("udpFlood"),
   HTTP_FLOOD("httpFlood"),
   SLOW_READ("slowRead"),
   SLOW_BODY("slowBody"),
   SLOW_HEADERS("slowHeaders"),
   NEPTUNE("neptune");

   private final String value;

   private SpecificAttackTypeEnum(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }
}
