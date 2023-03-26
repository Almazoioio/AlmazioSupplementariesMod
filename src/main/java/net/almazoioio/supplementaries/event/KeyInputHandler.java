package net.almazoioio.supplementaries.event;

import net.almazoioio.supplementaries.AlmazioData;
import net.almazoioio.supplementaries.AlmazioMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_ALMAZIO = "key.category.almaziosupplementaries.almaziosupplementaries";
    public static final String KEY_LEFT = "key.almaziosupplementaries.left";
    public static final String KEY_RIGHT = "key.almaziosupplementaries.right";
    public static final String KEY_TOGGLE = "key.almaziosupplementaries.toggle";
    public static KeyBinding toggleKey;
    public static KeyBinding leftKey;
    public static KeyBinding rightKey;


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
        registerKeyInputs();

    }

}
