package pl.edu.pg.eti.kask.list.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.model.Faction;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.entity.Skill;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;
import pl.edu.pg.eti.kask.list.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class InitializedData {

    private final UserService userService;

    private final ArmyService armyService;

    private final UnitService unitService;

    private final SquadService squadService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService, ArmyService armyService, UnitService unitService, SquadService squadService, RequestContextController requestContextController) {
        this.userService = userService;
        this.armyService = armyService;
        this.unitService = unitService;
        this.squadService = squadService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("System")
                .surname("Admin")
                .birthDate(LocalDate.of(1990, 10, 21))
                .email("admin@simplerpg.example.com")
                .password("adminadmin")
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();

        User test = User.builder()
                .id(UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851"))
                .login("test")
                .name("Test")
                .surname("Testowy")
                .birthDate(LocalDate.of(1995, 5, 15))
                .email("test@test.example.com")
                .password("testtest")
                .roles(List.of(UserRoles.USER))
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("kevin")
                .name("Kevin")
                .surname("Pear")
                .birthDate(LocalDate.of(2001, 1, 16))
                .email("kevin@example.com")
                .password("useruser")
                .roles(List.of(UserRoles.USER))
                .build();

        User alice = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("alice")
                .name("Alice")
                .surname("Grape")
                .birthDate(LocalDate.of(2002, 3, 19))
                .email("alice@example.com")
                .password("useruser")
                .roles(List.of(UserRoles.USER))
                .build();
        if (userService.findAll().isEmpty()) {
            userService.create(admin);
            userService.create(kevin);
            userService.create(alice);
            userService.create(test);
        }

        Skill attack = Skill.builder()
                .id(UUID.randomUUID())
                .name("Attack")
                .description("Attacks an enemy with light attack.")
                .build();

        Skill backStab = Skill.builder()
                .id(UUID.randomUUID())
                .name("Back stab")
                .description("Attacks an enemy from behind with medium attack.")
                .build();

        Skill heal = Skill.builder()
                .id(UUID.randomUUID())
                .name("Heal self")
                .description("Heals self.")
                .build();

        Skill charm = Skill.builder()
                .id(UUID.randomUUID())
                .name("Charm creature")
                .description("Charm creature and convinces to run away.")
                .build();

        Skill heavyAttack = Skill.builder()
                .id(UUID.randomUUID())
                .name("Heavy attack")
                .description("Attacks an enemy with heavy attack.")
                .build();

        Unit Intercessor = Unit.builder()
                .id(UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0"))
                .name("Intercessor")
                .movement(1)
                .save(1)
                .leadership(1)
                .toughness(3)
                .wounds(3)
                .description("Intercessor is the wound.")
                .portrait(getResourceAsByteArray("../avatar/intercessor.jpg"))//package relative path
                .build();

        Intercessor.addSkill(heavyAttack);

        Unit scout = Unit.builder()
                .id(UUID.fromString("d3f3b1e4-2f4a-4f0a-8e2e-6f5e8f1c9b7a"))
                .name("Scout")
                .movement(2)
                .save(2)
                .leadership(2)
                .toughness(2)
                .wounds(2)
                .description("Scout is the eyes and ears.")
                .portrait(getResourceAsByteArray("../avatar/scouts.png"))//package relative path
                .build();
        scout.addSkill(attack);

        Unit apothecary = Unit.builder()
                .id(UUID.fromString("a1b2c3d4-e5f6-7a8b-9c0d-e1f2a3b4c56f"))
                .name("apothecary")
                .movement(1)
                .save(3)
                .leadership(1)
                .toughness(2)
                .wounds(2)
                .description("apothecary is the life saver.")
                .portrait(getResourceAsByteArray("../avatar/apothecary.jpg"))//package relative path
                .build();
        apothecary.addSkill(heal);

        Unit aggressor = Unit.builder()
                .id(UUID.fromString("b1c2d3e4-f5a6-7b8c-9d0e-f1a2b3c4d5e6"))
                .name("Aggressor")
                .movement(1)
                .save(1)
                .leadership(2)
                .toughness(4)
                .wounds(4)
                .description("Aggressor is the frontline powerhouse.")
                .portrait(getResourceAsByteArray("../avatar/aggressors.jpg"))
                .build();

        Unit bloodclaw = Unit.builder()
                .id(UUID.fromString("c1d2e3f4-a5b6-7c8d-9e0f-a1b2c3d4e5f6"))
                .name("Bloodclaw")
                .movement(2)
                .save(2)
                .leadership(3)
                .toughness(3)
                .wounds(3)
                .description("Bloodclaw is a swift and deadly warrior.")
                .portrait(getResourceAsByteArray("../avatar/bloodclaw.jpg"))
                .build();

        Unit wg_battle_leader = Unit.builder()
                .id(UUID.fromString("d1e2f3a4-b5c6-7d8e-9f0a-b1c2d3e4f5a6"))
                .name("Wolf Guard Battle Leader")
                .movement(1)
                .save(1)
                .leadership(4)
                .toughness(4)
                .wounds(5)
                .description("Wolf Guard Battle Leader commands the pack with unmatched ferocity.")
                .portrait(getResourceAsByteArray("../avatar/wg_battle_leader.jpg"))
                .build();

        Unit wg_terminator = Unit.builder()
                .id(UUID.fromString("e1f2a3b4-c5d6-7e8f-9a0b-c1d2e3f4a5b6"))
                .name("Wolf Guard Terminator")
                .movement(1)
                .save(1)
                .leadership(3)
                .toughness(5)
                .wounds(6)
                .description("Wolf Guard Terminator is clad in the heaviest armor, ready to face any foe.")
                .portrait(getResourceAsByteArray("../avatar/wg_terminators.jpg"))
                .build();

        Unit wg_headtaker = Unit.builder()
                .id(UUID.fromString("f1a2b3c4-d5e6-7f8a-9b0c-d1e2f3a4b5c6"))
                .name("Wolf Guard Headtaker")
                .movement(2)
                .save(2)
                .leadership(2)
                .toughness(4)
                .wounds(4)
                .description("Wolf Guard Headtaker is known for his brutal efficiency in battle.")
                .portrait(getResourceAsByteArray("../avatar/wg_headtakers.jpg"))
                .build();

        Unit chaplain = Unit.builder()
                .id(UUID.fromString("a1b2c3d4-e5f6-7a8b-9c0d-e1f2a3b4c5d7"))
                .name("Chaplain")
                .movement(1)
                .save(2)
                .leadership(5)
                .toughness(3)
                .wounds(3)
                .description("Chaplain inspires his brethren to fight with unwavering faith.")
                .portrait(getResourceAsByteArray("../avatar/chaplain.jpg"))
                .build();

        if (unitService.findAll().isEmpty()) {
            unitService.create(scout);
            unitService.create(Intercessor);
            unitService.create(apothecary);
            unitService.create(aggressor);
            unitService.create(bloodclaw);
            unitService.create(wg_battle_leader);
            unitService.create(wg_terminator);
            unitService.create(wg_headtaker);
            unitService.create(chaplain);
        }

        Army AstraMilitarum = Army.builder()
                .id(UUID.fromString("f1e2d3c4-b5a6-7980-1a2b-3c4d5e6f7a8b"))
                .name("Astra Militarum Leman Russ Spam")
                .description("Tried a new list with Rogal Dorn as a commander. Lots of tanks, maily Leman Russ.")
                .faction(Faction.IMPERIUM)
                .owner(admin)
                .build();

        Army Orks = Army.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .name("Ork Waaagh!")
                .description("A horde of Orks led by Warboss Gorgutz.")
                .faction(Faction.XENOS)
                .owner(admin)
                .build();

        Army Aeldari = Army.builder()
                .id(UUID.fromString("223e4567-e89b-12d3-a456-426614174001"))
                .name("Aeldari Strike Force")
                .description("A swift and deadly Aeldari strike force.")
                .faction(Faction.XENOS)
                .owner(kevin)
                .build();

        Army ThousandSons = Army.builder()
                .id(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"))
                .name("Thousand Sons Sorcerers")
                .description("A legion of Thousand Sons led by Magnus the Red.")
                .faction(Faction.CHAOS)
                .owner(alice)
                .build();

        if (armyService.findAll().isEmpty()) {
            armyService.create(AstraMilitarum);
            armyService.create(Orks);
            armyService.create(Aeldari);
            armyService.create(ThousandSons);
        }

        Squad squad1 = Squad.builder()
                .id(UUID.fromString("423e4567-e89b-12d3-a456-426614174003"))
                .army(AstraMilitarum)
                .unit(Intercessor)
                .count(10)
                .build();

        Squad squad2 = Squad.builder()
                .id(UUID.fromString("523e4567-e89b-12d3-a456-426614174004"))
                .army(AstraMilitarum)
                .unit(apothecary)
                .count(2)
                .build();
        if (squadService.findAll().isEmpty()) {
            squadService.create(squad1);
            squadService.create(squad2);
        }


        requestContextController.deactivate();

    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }


}
