package xyz.ufactions.customstaff.libs;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import xyz.ufactions.customstaff.reflection.ReflectionUtils;

public class TitleAPI {

    // Class
    private static final ReflectionUtils.RefClass ClassCraftPlayer = ReflectionUtils.getRefClass("{cb}.entity.CraftPlayer");
    private static final ReflectionUtils.RefClass ClassEntityPlayer = ReflectionUtils.getRefClass("{nms}.EntityPlayer");
    private static final ReflectionUtils.RefClass ClassPlayerConnection = ReflectionUtils.getRefClass("{nms}.PlayerConnection");
    private static final ReflectionUtils.RefClass ClassPacket = ReflectionUtils.getRefClass("{nms}.Packet");

    private static final ReflectionUtils.RefClass ClassPacketPlayOutTitle = ReflectionUtils.getRefClass("{nms}.PacketPlayOutTitle");
    private static final ReflectionUtils.RefClass ClassIChatBaseComponent = ReflectionUtils.getRefClass("{nms}.IChatBaseComponent");
    private static final ReflectionUtils.RefClass ClassChatMessage = ReflectionUtils.getRefClass("{nms}.ChatMessage");
    private static final ReflectionUtils.RefClass ClassEnumTitleAction = ReflectionUtils.getRefClass("{nms}.PacketPlayOutTitle$EnumTitleAction");

    // Method
    private static final ReflectionUtils.RefMethod CraftPlayerMethodGetHandle = ClassCraftPlayer.getMethod("getHandle");
    private static final ReflectionUtils.RefMethod PlayerConnectionMethodSendPacket = ClassPlayerConnection.getMethod("sendPacket", ClassPacket);

    // Field
    private static final ReflectionUtils.RefField EntityPlayerFieldPlayerConnection = ClassEntityPlayer.getField("playerConnection");

    public static void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Player... players) {
        ReflectionUtils.RefConstructor ConstructorPacketPlayOutTitle = ClassPacketPlayOutTitle.getConstructor(ClassEnumTitleAction, ClassIChatBaseComponent);
        ReflectionUtils.RefConstructor ConstructorChatMessage = ClassChatMessage.getConstructor(String.class, Object[].class);

//        Object titlePacket = ConstructorPacketPlayOutTitle.create(Enum.valueOf(ClassEnumTitleAction.getRealClass(), ""), ConstructorChatMessage.create(title, null));
        Object subtitlePacket = ConstructorPacketPlayOutTitle.create(ClassEnumTitleAction.getMethod("valueOf").call("SUBTITLE"), ConstructorChatMessage.create(subtitle, null));

        for (Player player : players) {
            setTimings(fadeIn, stay, fadeOut, player);
//            sendPacket(player, titlePacket, subtitlePacket);
        }
    }

    private static void setTimings(int fadeIn, int stay, int fadeOut, Player player) {
        ReflectionUtils.RefConstructor ConstructorPacketPlayOutTitle = ClassPacketPlayOutTitle.getConstructor(Integer.class, Integer.class, Integer.class);
        Object packetPlayOutTitle = ConstructorPacketPlayOutTitle.create(fadeIn, stay, fadeOut);
        sendPacket(player, packetPlayOutTitle);
    }

    private static void sendPacket(Player player, Object... packets) {
        Validate.notEmpty(packets, "Packet array empty");

        Object handle = CraftPlayerMethodGetHandle.of(player).call();
        Object connection = EntityPlayerFieldPlayerConnection.of(handle).get();

        for (Object packet : packets) {
            Validate.isTrue(packet.getClass().isInstance(ClassPacket), "'" + packet + "' isn't packet");

            PlayerConnectionMethodSendPacket.of(connection).call(packet);
        }
    }
}