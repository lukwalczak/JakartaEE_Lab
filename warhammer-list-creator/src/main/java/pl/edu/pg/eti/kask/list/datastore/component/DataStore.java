package pl.edu.pg.eti.kask.list.datastore.component;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using a data source object which should
 * be put in servlet context in a single instance. In order to avoid {@link java.util.ConcurrentModificationException}
 * all methods are synchronized. Normally synchronization would be carried on by the database server. Caution, this is
 * very inefficient implementation but can be used to present other mechanisms without obscuration example with ORM
 * usage.
 */
@Log
public class DataStore {

    /**
     * Set of all characters.
     */
    private final Set<Unit> units = new HashSet<>();

    /**
     * Set of all users.
     */
    private final Set<User> users = new HashSet<>();

    private final Set<Army> armies = new HashSet<>();

    /**
     * Component used for creating deep copies.
     */
    private final CloningUtility cloningUtility;

    /**
     * @param cloningUtility component used for creating deep copies
     */
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }


    /**
     * Seeks for all characters.
     *
     * @return list (can be empty) of all characters
     */
    public synchronized List<Unit> findAllUnits() {
        return units.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new character.
     *
     * @param value new character to be stored
     * @throws IllegalArgumentException if character with provided id already exists or when {@link User} or
     */
    public synchronized void createUnit(Unit value) throws IllegalArgumentException {
        if (units.stream().anyMatch(character -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(value.getId()));
        }
//        Unit entity = cloneWithRelationships(value);
        Unit entity = cloningUtility.clone(value);
        units.add(entity);
    }

    /**
     * Updates existing character.
     *
     * @param value character to be updated
     * @throws IllegalArgumentException if character with the same id does not exist or when {@link User} or
     */
    public synchronized void updateUnit(Unit value) throws IllegalArgumentException {
//        Unit entity = cloneWithRelationships(value);
        Unit entity = cloningUtility.clone(value);
        if (units.removeIf(character -> character.getId().equals(value.getId()))) {
            units.add(entity);
        } else {
            throw new IllegalArgumentException("The character with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing character.
     *
     * @param id id of character to be deleted
     * @throws IllegalArgumentException if character with provided id does not exist
     */
    public synchronized void deleteUnit(UUID id) throws IllegalArgumentException {
        if (!units.removeIf(character -> character.getId().equals(id))) {
            throw new IllegalArgumentException("The character with id \"%s\" does not exist".formatted(id));
        }
    }

    /**
     * Seeks for all users.
     *
     * @return list (can be empty) of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param value new user to be stored
     * @throws IllegalArgumentException if user with provided id already exists
     */
    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(character -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    /**
     * Updates existing user.
     *
     * @param value user to be updated
     * @throws IllegalArgumentException if user with the same id does not exist
     */
    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(character -> character.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void createArmy(Army value) throws IllegalArgumentException {
        if(armies.stream().anyMatch(army -> army.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The army id \"%s\" is not unique".formatted(value.getId()));
        }
        armies.add(cloningUtility.clone(value));
    }

    public synchronized void updateArmy(Army value) throws IllegalArgumentException {
        if (armies.removeIf(army -> army.getId().equals(value.getId()))) {
            armies.add(cloningUtility.clone(value));
        } else  {
            throw new IllegalArgumentException("The army with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteArmy(UUID id) throws IllegalArgumentException {
        if (!armies.removeIf(army -> army.getId().equals(id))) {
            throw new IllegalArgumentException("The army with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Army> findAllArmies() {
        return armies.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Clones existing character and updates relationships for values in storage
     *
     * @param value character
     * @return cloned value with updated relationships
     * @throws IllegalArgumentException when {@link User} or {@link Profession} with provided uuid does not exist
     */
//    private Unit cloneWithRelationships(Unit value) {
//        Unit entity = cloningUtility.clone(value);
//
//        if (entity.getUser() != null) {
//            entity.setUser(users.stream()
//                    .filter(user -> user.getId().equals(value.getUser().getId()))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
//        }
//
//        if (entity.getProfession() != null) {
//            entity.setProfession(professions.stream()
//                    .filter(profession -> profession.getId().equals(value.getProfession().getId()))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("No profession with id \"%s\".".formatted(value.getProfession().getId()))));
//        }
//
//        return entity;
//    }

}
