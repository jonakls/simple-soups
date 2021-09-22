package me.gardendev.simplesoups.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleFactory {

    private final String title;
    private final String subTitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public TitleFactory(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public void setTime(int fadeIn, int stay, int fadeOut) {
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(Player player) {
        PacketPlayOutTitle packet1 = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TIMES,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"),
                this.fadeIn, this.stay, this.fadeOut
        );
        PacketPlayOutTitle packet2 = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TIMES,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}"),
                this.fadeIn, this.stay, this.fadeOut
        );
        PacketPlayOutTitle packet3 = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}")
        );
        PacketPlayOutTitle packet4 = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}")
        );
        sendPacket(player, packet1);
        sendPacket(player, packet2);
        sendPacket(player, packet3);
        sendPacket(player, packet4);
    }

    private static void sendPacket(Player player, Packet<?> packet) {
        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packet);
    }
}
