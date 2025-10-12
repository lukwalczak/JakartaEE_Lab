package pl.edu.pg.eti.kask.list.component;

import pl.edu.pg.eti.kask.list.army.dto.function.ArmiesToResponseFunction;
import pl.edu.pg.eti.kask.list.army.dto.function.ArmyToResponseFunction;
import pl.edu.pg.eti.kask.list.army.dto.function.RequestToArmyFunction;
import pl.edu.pg.eti.kask.list.army.dto.function.UpdateArmyWithRequestFunction;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.function.UnitToResponseFunction;
import pl.edu.pg.eti.kask.list.unit.dto.function.UnitsToResponseFunction;
import pl.edu.pg.eti.kask.list.unit.dto.function.RequestToUnitFunction;
import pl.edu.pg.eti.kask.list.unit.dto.function.UpdateUnitWithRequestFunction;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.list.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.list.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.list.user.dto.function.RequestToUserFunction;
import pl.edu.pg.eti.kask.list.user.dto.function.UpdateUserPasswordWithRequestFunction;
import pl.edu.pg.eti.kask.list.user.dto.function.UpdateUserWithRequestFunction;
import pl.edu.pg.eti.kask.list.user.dto.function.UserToResponseFunction;
import pl.edu.pg.eti.kask.list.user.dto.function.UsersToResponseFunction;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
public class DtoFunctionFactory {

    /**
     * Returns a function to convert a single {@link Unit} to {@link GetUnitResponse}.
     *
     * @return UnitToResponseFunction instance
     */
    public UnitToResponseFunction unitToResponse() {
        return new UnitToResponseFunction();
    }


    /**
     * Returns a function to convert a list of {@link Unit} to {@link GetUnitsResponse}.
     *
     * @return UnitsToResponseFunction instance
     */
    public UnitsToResponseFunction unitsToResponse() {
        return new UnitsToResponseFunction();
    }


    /**
     * Returns a function to convert a {@link PutUnitRequest} to a {@link Unit}.
     *
     * @return RequestToUnitFunction instance
     */
    public RequestToUnitFunction requestToUnit() {
        return new RequestToUnitFunction();
    }

    /**
     * Returns a function to update a {@link Unit}.
     *
     * @return UpdateUnitFunction instance
     */
    public UpdateUnitWithRequestFunction updateUnit() {
        return new UpdateUnitWithRequestFunction();
    }

    /**
     * Returns a function to convert a {@link PutUserRequest} to a {@link User}.
     *
     * @return RequestToUserFunction instance
     */
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    /**
     * Returns a function to update a {@link User}.
     *
     * @return UpdateUserFunction instance
     */
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    /**
     * Returns a function to update a {@link User}'s password.
     *
     * @return UpdateUserPasswordFunction instance
     */
    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }

    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public ArmyToResponseFunction armyToResponse() {return new ArmyToResponseFunction();}
    public ArmiesToResponseFunction armiesToResponseFunction() {return new ArmiesToResponseFunction();}
    public RequestToArmyFunction  requestToArmy() {return new RequestToArmyFunction();}
    public UpdateArmyWithRequestFunction  updateArmy() {return new UpdateArmyWithRequestFunction();}

}
