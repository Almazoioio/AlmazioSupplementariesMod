package net.almazoioio.supplementaries;

import net.almazoioio.supplementaries.client.AlmazioChat;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.*;

public class AlmazioData {
    public static boolean showChat = true;
    public static boolean isFade = true;
    public static boolean isFadeSwitched = true;
    public static boolean isChatSwitched = false;
    public static long chatSwitched = Calendar.getInstance().getTimeInMillis();
    public static boolean log = true;
    public static List<OrderedText> visibleMsg = new ArrayList<OrderedText>();
    public static int visibleMsgs = 20;
    public static List<OrderedText> appending = new ArrayList<OrderedText>();
    public static List<OrderedText> deappending = new ArrayList<OrderedText>();

    public static long y;
    public static long[] timeMsg = new long[0];
    public static long[] append(long[] list, long num)
    {
        long[] result = new long[list.length + 1];
        for(int i = 0; i < list.length; i++)
        {
            result[i] = list[i];
        }
        result[list.length] = num;
        return result;
    }
    public static int[] appendi(int[] list, int num)
    {
        int[] result = new int[list.length + 1];
        for(int i = 0; i < list.length; i++)
        {
            result[i] = list[i];
        }
        result[list.length] = num;
        return result;
    }
    public static long[] deAppend(long[] list) {
        long[] result = new long[list.length - 1];
        for (int i = 0; i < list.length - 1; i++) {
            result[i] = list[i];
        }
        return result;
    }
    //public static long time() {return Calendar.getInstance().getTimeInMillis();}
    public static int scroll = 0;
    public static int lastScroll = 0;
    public static int mode = 0;
    public static int modes = 0;
    public static List<AlmazioChat> chats = new ArrayList<AlmazioChat>();
    public static int[] len = new int[0];
    public static void regAll(int num)
    {
        modes = num;
        for(int i = 0; i<num; i++)
        {
            chats.add(new AlmazioChat());
            len = appendi(len,0);
        }
    }
    public static void addMsg(Text pretext) {
//        for(int i = 0; i < visibleMsg.size(); i++) {
//            float offsety = 10 - Math.max(0f,Math.min(10f,
//                    (float)(1000D/(double)(Calendar.getInstance().getTimeInMillis()-y
//                    ))));
//
//            AlmazioMod.LOGGER.info(Float.toString(offsety));
//        }

        chats.get(0).msg.add(pretext);
        boolean isMention = false;
        String playerName = AlmazioMod.client.player.getName().getString();
        if (pretext.getString().split(playerName).length > 1) {
            chats.get(1).msg.add(pretext);
            isMention = true;
        }

//        ClientPlayNetworkHandler clientPlayNetworkHandler = AlmazioMod.client.player.networkHandler;
//        List<PlayerListEntry> list = clientPlayNetworkHandler.getListedPlayerListEntries().stream().toList();
        boolean isOther = true;
        String[] players = new String[AlmazioMod.client.world.getPlayers().size()];
        for(int l = 0; l < AlmazioMod.client.world.getPlayers().size(); l++) {
            players[l] = AlmazioMod.client.world.getPlayers().get(l).getDisplayName().getString();
        }
        for (String player : players) {
            if (pretext.getString().split(player).length > 1) {
                isOther = false;
            }
        }
        if (isOther) {
            chats.get(2).msg.add(pretext);
        }

        List<OrderedText> temp = ChatMessages.breakRenderedChatMessageLines(chats.get(0).msg.get(chats.get(0).msg.size() - 1), 320, AlmazioMod.client.textRenderer);
        len[0] = temp.size();
        for (OrderedText text : temp) {
            chats.get(0).orderedMsg.add(text);
        }

        if (isMention) {
            temp = ChatMessages.breakRenderedChatMessageLines(chats.get(1).msg.get(chats.get(1).msg.size() - 1), 320, AlmazioMod.client.textRenderer);
            len[1] = temp.size();
            for (OrderedText text : temp) {
                chats.get(1).orderedMsg.add(text);
            }
        }

        if (isOther) {
            temp = ChatMessages.breakRenderedChatMessageLines(chats.get(2).msg.get(chats.get(2).msg.size() - 1), 320, AlmazioMod.client.textRenderer);
            len[2] = temp.size();
            for (OrderedText text : temp) {
                chats.get(2).orderedMsg.add(text);
            }
        }

        List<OrderedText> texts = chats.get(mode).orderedMsg;
        int size = texts.size();

        for(int j = 0; j < len[mode]; j++) {
            if (visibleMsg.size() < visibleMsgs) {
                appending.add(texts.get(size - len[mode] + j));
                y = Calendar.getInstance().getTimeInMillis();
            } else {
                for (int i = 1; i < visibleMsgs; i++) {
                    visibleMsg.set(i - 1, visibleMsg.get(i));
                }
                deappending.add(visibleMsg.get(visibleMsgs - 1));
                appending.add(texts.get(size - 1));
                y = Calendar.getInstance().getTimeInMillis();
            }

            if (timeMsg.length < visibleMsgs) {
                timeMsg = append(timeMsg, Calendar.getInstance().getTimeInMillis());
            } else {
                for (int i = 1; i < visibleMsgs; i++) {
                    timeMsg[i - 1] = timeMsg[i];
                }
                timeMsg = deAppend(timeMsg);
                timeMsg = append(timeMsg, Calendar.getInstance().getTimeInMillis());
            }
        }
    }
    public static void switchChat() {
        isChatSwitched = true;
        visibleMsg.clear();
        List<OrderedText> texts = chats.get(mode).orderedMsg;
        int size = texts.size();

        if (size < visibleMsgs) {

            for (OrderedText text : texts) {
                if (visibleMsg.size() < visibleMsgs) {
                    visibleMsg.add(text);
                } else {
                    for (int i = 1; i < visibleMsgs; i++) {
                        visibleMsg.set(i - 1, visibleMsg.get(i));
                    }
                    visibleMsg.remove(visibleMsgs - 1);
                    visibleMsg.add(texts.get(size - 1));
                }
            }
        } else {
            List<OrderedText> temp = new ArrayList<OrderedText>();

            for (int i = size - visibleMsgs; i < size; i++) {
                temp.add(texts.get(i));
            }

            for (OrderedText text : temp) {
                if (visibleMsg.size() < visibleMsgs) {
                    visibleMsg.add(text);
                } else {
                    for (int i = 1; i < visibleMsgs; i++) {
                        visibleMsg.set(i - 1, visibleMsg.get(i));
                    }
                    visibleMsg.remove(visibleMsgs - 1);
                    visibleMsg.add(texts.get(size - 1));
                }
            }
        }
    }
    public static void appendMsg(OrderedText text)
    {
        if (visibleMsg.size() < visibleMsgs) {
            visibleMsg.add(text);
        } else {
            for (int i = 1; i < visibleMsgs; i++) {
                visibleMsg.set(i - 1, visibleMsg.get(i));
            }
            visibleMsg.remove(visibleMsgs - 1);
            visibleMsg.add(text);
        }
        if (timeMsg.length < visibleMsgs) {
            timeMsg = append(timeMsg, Calendar.getInstance().getTimeInMillis());
        } else {
            for (int i = 1; i < visibleMsgs; i++) {
                timeMsg[i - 1] = timeMsg[i];
            }
            timeMsg = deAppend(timeMsg);
            timeMsg = append(timeMsg, Calendar.getInstance().getTimeInMillis());
        }
    }

    public static void switchFade() {
        isFadeSwitched = true;
        visibleMsg.clear();

        List<OrderedText> texts = chats.get(mode).orderedMsg;
        int size = texts.size();

        List<OrderedText> temp = new ArrayList<OrderedText>();
        if (size < visibleMsgs) {

            for (OrderedText text : texts) {
                if (visibleMsg.size() < visibleMsgs) {
                    visibleMsg.add(text);
                } else {
                    for (int i = 1; i < visibleMsgs; i++) {
                        visibleMsg.set(i - 1, visibleMsg.get(i));
                    }
                    visibleMsg.remove(visibleMsgs - 1);
                    visibleMsg.add(texts.get(size - 1));
                }
            }
        } else {

            for (int i = size - visibleMsgs; i < size; i++) {
                temp.add(texts.get(i));
            }

            for (OrderedText text : temp) {
                if (visibleMsg.size() < visibleMsgs) {
                    visibleMsg.add(text);
                } else {
                    for (int i = 1; i < visibleMsgs; i++) {
                        visibleMsg.set(i - 1, visibleMsg.get(i));
                    }
                    visibleMsg.remove(visibleMsgs - 1);
                    visibleMsg.add(texts.get(size - 1));
                }
            }
        }

        for (int i = 0; i < visibleMsgs; i++)
        {
            timeMsg = append(timeMsg, Calendar.getInstance().getTimeInMillis());
        }

    }


    public static void updateScroll() {

        List<OrderedText> texts = chats.get(mode).orderedMsg;
        int size = texts.size();

        AlmazioMod.LOGGER.info(Integer.toString(size - visibleMsgs - scroll));
        if (lastScroll != scroll) {
            visibleMsg.clear();
            timeMsg = new long[0];



            List<OrderedText> temp = new ArrayList<OrderedText>();

            if(size - visibleMsgs - scroll > 0) {
                for (int i = size - visibleMsgs - scroll; i < size - scroll; i++) {
                    temp.add(texts.get(i));
                }
            }  else
            {
                scroll = size - visibleMsgs;
                for (int i = 0; i < visibleMsgs; i++) {
                    temp.add(texts.get(i));
                }
            }

            for (OrderedText text : temp) {
                if (visibleMsg.size() < visibleMsgs) {
                    visibleMsg.add(text);
                } else {
                    for (int i = 1; i < visibleMsgs; i++) {
                        visibleMsg.set(i - 1, visibleMsg.get(i));
                    }
                    visibleMsg.remove(visibleMsgs - 1);
                    visibleMsg.add(texts.get(size - 1));
                }
            }
            lastScroll = scroll;
        }
    }
}
