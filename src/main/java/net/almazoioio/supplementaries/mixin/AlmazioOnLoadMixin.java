package net.almazoioio.supplementaries.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class AlmazioOnLoadMixin {
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		minecraft.inGameHud.getChatHud().addMessage(Text.literal("[ALS] Almazoioio Supplementaries!"));
	}
}