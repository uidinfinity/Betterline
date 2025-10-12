package net.shoreline.client.impl.manager;

import net.shoreline.client.Shoreline;
import net.shoreline.client.ShorelineMod;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.impl.module.client.*;
import net.shoreline.client.impl.module.combat.*;
import net.shoreline.client.impl.module.exploit.*;
import net.shoreline.client.impl.module.misc.*;
import net.shoreline.client.impl.module.movement.*;
import net.shoreline.client.impl.module.render.*;
import net.shoreline.client.impl.module.world.*;

import java.util.*;

/**
 * @author linus
 * @since 1.0
 */
public class ModuleManager {
    // The client module register. Keeps a list of modules and their ids for
    // easy retrieval by id.
    private final Map<String, Module> modules =
            Collections.synchronizedMap(new LinkedHashMap<>());

    /**
     * Initializes the module register.
     */
    public ModuleManager() {
        // MAINTAIN ALPHABETICAL ORDER
        register(
                // Client
                new ServerModule(),
                new CapesModule(),
                new ClickGuiModule(),
                new ColorsModule(),
                new ChatModule(),
                new AnticheatModule(),
                new ServerModule(),
                new HUDModule(),
                new RotationsModule(),
                new FontModule(),
                // Combat
                new AuraModule(),
                new AutoArmorModule(),
                new AutoBowReleaseModule(),
                new AutoCrystalModule(),
                new AutoLogModule(),
                new AutoTotemModule(),
                new AutoTrapModule(),
                new AutoXPModule(),
                new BackTrackModule(),
                new BlockLagModule(),
                new BowAimModule(),
                new CriticalsModule(),
                new HoleFillModule(),
                new NoHitDelayModule(),
                new ReplenishModule(),
                new SelfBowModule(),
                new SelfTrapModule(),
                new SurroundModule(),
                new TriggerModule(),
                // Exploit
                new AntiHungerModule(),
                new ChorusControlModule(),
                new ClientSpoofModule(),
                new BedAuraModule(),
                new CrasherModule(),
                new NewChunksModule(),
                new DisablerModule(),
                new ExtendedFireworkModule(),
                new FakeLatencyModule(),
                new FastProjectileModule(),
                new PacketCancelerModule(),
                new PacketFlyModule(),
                new FastEatModule(),
                new PhaseModule(),
                new PortalGodModeModule(),
                new ReachModule(),
                // Misc
                new AntiAFKModule(),
                new AntiAimModule(),
                new AutoMapartModule(),
                new SpammerModule(),
                //new AntiBookBanModule(),
                new AntiSpamModule(),
                new AutoAcceptModule(),
                new AutoEatModule(),
                new AutoFishModule(),
                new AutoReconnectModule(),
                new AutoRespawnModule(),
                new BeaconSelectorModule(),
                // new BetterChatModule(),
                new ChatNotifierModule(),
                new ChestSwapModule(),
                // new ChestStealerModule(),
                new FakePlayerModule(),
                new InvCleanerModule(),
                new MiddleClickModule(),
                new NoPacketKickModule(),
                new NoSoundLagModule(),
                new TimerModule(),
                new TrueDurabilityModule(),
                new UnfocusedFPSModule(),
                new XCarryModule(),
                // Movement
                new AntiLevitationModule(),
                new AutoWalkModule(),
                new SneakModule(),
                new ElytraFlyModule(),
                new EntityControlModule(),
                new EntitySpeedModule(),
                new FakeLagModule(),
                new FastFallModule(),
                new FlightModule(),
                new IceSpeedModule(),
                new JesusModule(),
                new LongJumpModule(),
                new NoFallModule(),
                new NoJumpDelayModule(),
                new NoSlowModule(),
                new ParkourModule(),
                new SpeedModule(),
                new SprintModule(),
                new StepModule(),
                new TickShiftModule(),
                new TridentFlyModule(),
                new VelocityModule(),
                new YawModule(),
                // Render
                new BlockHighlightModule(),
                new BreakHighlightModule(),
                new ChamsModule(),
                new ESPModule(),
                new ExtraTabModule(),
                new FreecamModule(),
                new FullbrightModule(),
                new HoleESPModule(),
                new NameProtectModule(),
                new NametagsModule(),
                new BreadcrumbsModule(),
                new NoRenderModule(),
                new NoRotateModule(),
                new NoWeatherModule(),
                new CrosshairModule(),
                new ParticlesModule(),
                new SkeletonModule(),
                new SkyboxModule(),
                new ShadersModule(),
                new ZoomModule(),
                new TooltipsModule(),
                new SearchModule(),
                new TracersModule(),
                new TrueSightModule(),
                new ViewClipModule(),
                new ViewModelModule(),
                 new WaypointsModule(),
                new TrajectoriesModule(),
                // World
                new AntiInteractModule(),
                new AutoMineModule(),
                new XRayModule(),
                new AutoToolModule(),
                new AvoidModule(),
                new BlockInteractModule(),
                new FastDropModule(),
                new FastPlaceModule(),
                new MultitaskModule(),
                new NoGlitchBlocksModule(),
                new ScaffoldModule(),
                new AutoTunnelModule(),
                new SpeedmineModule(),
                new SocialsModule()
                // new WallhackModule()
        );
        if (ShorelineMod.isBaritonePresent()) {
            register(new BaritoneModule());
        }
        Shoreline.info("Registered {} modules!", modules.size());
    }

    /**
     *
     */
    public void postInit() {
        // TODO
    }

    /**
     * @param modules
     * @see #register(Module)
     */
    private void register(Module... modules) {
        for (Module module : modules) {
            register(module);
        }
    }

    /**
     * @param module
     */
    private void register(Module module) {
        modules.put(module.getId(), module);
    }

    /**
     * @param id
     * @return
     */
    public Module getModule(String id) {
        return modules.get(id);
    }

    /**
     * @return
     */
    public List<Module> getModules() {
        return new ArrayList<>(modules.values());
    }
}
