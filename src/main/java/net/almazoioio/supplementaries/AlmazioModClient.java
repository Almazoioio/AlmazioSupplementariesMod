package net.almazoioio.supplementaries;

import net.almazoioio.supplementaries.client.AlmazioRenderer;
import net.almazoioio.supplementaries.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class AlmazioModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient()
    {
        KeyInputHandler.register();

        HudRenderCallback.EVENT.register(new AlmazioRenderer());

        AlmazioData.regAll(3);
        AlmazioRenderer.reg();
    }
}
