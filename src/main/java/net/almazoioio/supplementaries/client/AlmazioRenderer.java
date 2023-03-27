package net.almazoioio.supplementaries.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.almazoioio.supplementaries.AlmazioData;
import net.almazoioio.supplementaries.AlmazioMod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlmazioRenderer implements HudRenderCallback {
    private static final Identifier CHATGUIALL = new Identifier(AlmazioMod.ID,"textures/gui/chatguiall.png");
    private static final Identifier CHATGUIMEN = new Identifier(AlmazioMod.ID,"textures/gui/chatguimen.png");
    private static final Identifier CHATGUIOTH = new Identifier(AlmazioMod.ID,"textures/gui/chatguioth.png");
    private static final List<Identifier> textures = new ArrayList<>();
    public static void reg()
    {
        textures.add(CHATGUIALL);
        textures.add(CHATGUIMEN);
        textures.add(CHATGUIOTH);
    }

    @Override
    public void onHudRender(MatrixStack matrix, float tickDelta)
    {
        int x = 0;
        int y;
        float s = 1000.0f / 1080.0f;
        float offsety = 0;
        float height;
        int alpha = 0xFF;
        boolean one = false;
        if(AlmazioData.showChat && (AlmazioMod.client != null))
        {
            height = (float) AlmazioMod.client.getWindow().getScaledHeight();


            if(AlmazioData.y != 0) {
                offsety = 10f - Math.max(0f, Math.min(10f,
                        (float) (1000D / (double) (Calendar.getInstance().getTimeInMillis() - AlmazioData.y)
                        )));

            }

            y = (int) (height * s);
//            if (Calendar.getInstance().getTimeInMillis() - AlmazioData.y > 200)
//            {
//                offsetx = 320f - Math.max(0f, Math.min(320f,
//                        (float) (32000D / (double) (Calendar.getInstance().getTimeInMillis() - AlmazioData.start)
//                        )));
//                if(AlmazioData.start == 0)
//                {
//                    AlmazioData.start = Calendar.getInstance().getTimeInMillis();
//                }
//            }

            if (AlmazioData.isFade) {
                if (AlmazioData.isFadeSwitched) {
                    RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, Math.max(0, Math.min(1,
                            (float) ((1000D / (double) (Calendar.getInstance().getTimeInMillis() - AlmazioData.chatSwitched
                            ))))));
                    RenderSystem.setShaderTexture(0, textures.get(AlmazioData.mode));
                    DrawableHelper.drawTexture(matrix, x, y, 0, 0, 167 * 2, 13 * 2, 167 * 2, 13 * 2);
                    if (AlmazioData.isChatSwitched) {
                        AlmazioData.timeMsg = new long[0];
                        for (int i = 0; i < AlmazioData.chats.get(AlmazioData.mode).orderedMsg.size(); i++) {
                            AlmazioData.timeMsg = AlmazioData.append(AlmazioData.timeMsg, Calendar.getInstance().getTimeInMillis());
                        }
                        AlmazioData.isChatSwitched = false;
                    }
                    if (AlmazioData.chats.get(AlmazioData.mode).orderedMsg.size() > 0) {
                        for (int i = 0; i < AlmazioData.visibleMsg.size(); i++) {
                            alpha = Math.max(0, Math.min(255,
                                    (int) ((255000D / (double) (Calendar.getInstance().getTimeInMillis() - AlmazioData.timeMsg[AlmazioData.timeMsg.length - i - 1]
                                    )))));

                            AlmazioMod.client.textRenderer.drawWithShadow(matrix,
                                    AlmazioData.visibleMsg.get(AlmazioData.visibleMsg.size() - i - 1),
                                    5.0f, y - offsety - 10.0f - 10.0f * i, 0x00FFFFFF + (alpha << 24));
                        }
                    }
                }

            } else
            {
                AlmazioData.updateScroll();

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.setShaderTexture(0, textures.get(AlmazioData.mode));
                DrawableHelper.drawTexture(matrix, x, y, 0, 0, 167 * 2, 13 * 2, 167 * 2, 13 * 2);
                if (AlmazioData.isChatSwitched) {
                    AlmazioData.timeMsg = new long[0];
                    for (int i = 0; i < AlmazioData.chats.get(AlmazioData.mode).orderedMsg.size(); i++) {
                        AlmazioData.timeMsg = AlmazioData.append(AlmazioData.timeMsg, Calendar.getInstance().getTimeInMillis());
                    }
                    AlmazioData.isChatSwitched = false;
                }
                if (AlmazioData.chats.get(AlmazioData.mode).orderedMsg.size() > 0) {
                    for (int i = 0; i < AlmazioData.visibleMsg.size(); i++) {
                        AlmazioMod.client.textRenderer.drawWithShadow(matrix,
                                AlmazioData.visibleMsg.get(AlmazioData.visibleMsg.size() - i - 1),
                                5.0f, y - offsety - 10.0f - 10.0f * i, 0xFFFFFFFF);
                    }
                }
            }

            if (AlmazioData.appending.size() != 0) {
                AlmazioMod.client.textRenderer.drawWithShadow(matrix,
                        AlmazioData.appending.get(0),
                        5f, y - 10.0f, 0x00FFFFFF + ((int) (offsety * 25.5) << 24));
                if (AlmazioData.deappending.size() != 0) {
                    AlmazioMod.client.textRenderer.drawWithShadow(matrix,
                            AlmazioData.deappending.get(0),
                            5f, y - 10.0f, 0x00FFFFFF + ((int) (255 - offsety * 25.5) << 24));
                }
                if (offsety > 9.5f || ((offsety < 0.1f) && (Calendar.getInstance().getTimeInMillis() - AlmazioData.y > 200)) && !one) {
                    one = true;
//                    AlmazioData.start = 0;
                    AlmazioData.y = 0;
                    AlmazioData.appendMsg(AlmazioData.appending.get(0));
                    AlmazioData.appending.remove(0);
                    if (AlmazioData.deappending.size() != 0) {
                        AlmazioData.deappending.remove(0);
                    }

                }
            }

            if (AlmazioData.isFade) {
                if (AlmazioData.isFadeSwitched) {
                    for (int i = 0; i < AlmazioData.visibleMsg.size(); i++) {
                        alpha = Math.max(0, Math.min(255,
                                (int) ((255000D / (double) (Calendar.getInstance().getTimeInMillis() - AlmazioData.timeMsg[AlmazioData.timeMsg.length - i - 1]
                                )))));
                        if (alpha < 6) {
                            AlmazioData.visibleMsg.remove(i);
                            AlmazioData.timeMsg = AlmazioData.deAppend(AlmazioData.timeMsg);
                        }
                    }
                }
            }
        }
    }
}
