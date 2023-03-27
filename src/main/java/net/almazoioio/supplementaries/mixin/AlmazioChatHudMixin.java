package net.almazoioio.supplementaries.mixin;

import net.almazoioio.supplementaries.AlmazioData;
import net.almazoioio.supplementaries.AlmazioMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.net.Inet4Address;

@Mixin(ChatHud.class)
public class AlmazioChatHudMixin {
	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	private void almazioRender(MatrixStack matrices, int currentTick, int mouseX, int mouseY, CallbackInfo info) {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		if (!AlmazioData.showChat) {
		}
		else {
			info.cancel();
		}
	}
	@Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V")
	private void almazioAddMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh, CallbackInfo info) {
		if(AlmazioData.log) {
			AlmazioData.addMsg(message);
		}
		else
		{
			AlmazioData.log = true;
		}
	}
	@Inject(at = @At("HEAD"), method = "scroll", locals = LocalCapture.CAPTURE_FAILHARD)
	private void almazioScroll(int scroll, CallbackInfo info) {
		if (AlmazioData.scroll + scroll > 0) {
			AlmazioData.scroll += scroll;
		} else
		{
			AlmazioData.scroll = 0;
		}
		AlmazioMod.LOGGER.info(Integer.toString(AlmazioData.scroll)+" scroll");
	}
}