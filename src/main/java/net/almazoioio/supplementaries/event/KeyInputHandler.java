package net.almazoioio.supplementaries.event;

import net.almazoioio.supplementaries.AlmazioData;
import net.almazoioio.supplementaries.AlmazioMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Calendar;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_ALMAZIO = "key.category.almaziosupplementaries.almaziosupplementaries";
    public static final String KEY_LEFT = "key.almaziosupplementaries.left";
    public static final String KEY_RIGHT = "key.almaziosupplementaries.right";
    public static final String KEY_TOGGLE = "key.almaziosupplementaries.toggle";
    public static final String KEY_FADE = "key.almaziosupplementaries.fade";
    public static KeyBinding toggleKey;
    public static KeyBinding leftKey;
    public static KeyBinding rightKey;
    public static KeyBinding fadeKey;


    public static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client ->
            {
                if (toggleKey.wasPressed()) {
                    if (AlmazioData.showChat) {
                        AlmazioData.showChat = false;
                        AlmazioMod.LOGGER.info("Chat isn't rendering!");
                    } else {
                        AlmazioData.showChat = true;
                        AlmazioMod.LOGGER.info("Chat is rendering!");
                    }
                }

                if(rightKey.wasPressed())
                {
                    AlmazioData.chatSwitched = Calendar.getInstance().getTimeInMillis();
                    if(AlmazioData.mode+1 > AlmazioData.modes-1)
                    {
                        AlmazioData.mode = 0;
                        AlmazioData.switchChat();
                    } else
                    {
                        AlmazioData.mode++;
                        AlmazioData.switchChat();
                    }
                }
                if(leftKey.wasPressed())
                {
                    AlmazioData.chatSwitched = Calendar.getInstance().getTimeInMillis();
                    if(AlmazioData.mode-1 < 0)
                    {
                        AlmazioData.mode = 2;
                        AlmazioData.switchChat();
                    } else
                    {
                        AlmazioData.mode--;
                        AlmazioData.switchChat();
                    }
                }
                if (fadeKey.wasPressed())
                {
                    if (AlmazioData.isFade) {
                        AlmazioData.isFade = false;
                        AlmazioData.scroll = 0;
                        AlmazioData.lastScroll = 0;
                        AlmazioData.isFadeSwitched = false;
                    } else {
                        AlmazioData.isFade = true;

                        AlmazioData.switchFade();
                        AlmazioMod.LOGGER.info("WTF");
                    }

                }
        });
    }
    public static void register()
    {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                KEY_CATEGORY_ALMAZIO
        ));
        leftKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_LEFT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                KEY_CATEGORY_ALMAZIO
        ));
        rightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_RIGHT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                KEY_CATEGORY_ALMAZIO
        ));
        fadeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FADE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                KEY_CATEGORY_ALMAZIO
        ));
        registerKeyInputs();

    }

}
