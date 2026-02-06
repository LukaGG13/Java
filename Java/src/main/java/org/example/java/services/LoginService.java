package org.example.java.services;

import org.example.java.entity.admin.Admin;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.repository.Repository;
import org.example.java.entity.user.User;
import org.example.java.files.PasswordChecker;

public record LoginService(Repository repository) {
    //get user igs

    private boolean authentication(User user, String password) {
        return PasswordChecker.check(user.getId(), password);
    }


    public void login(String name, String password) {
        //TODO: stavi u db zbog speed il ne igs nije bitno
        var resultList = repository.getUsers().stream().filter(user -> user.getName().equals(name)).toList();
        if (resultList.isEmpty()) return; //TODO: ILI throw exeption mislim da cu trhowat coustom exeptionen tu da se rijseim ovoh uvjeta za prjektni i bolej je bacat expetion
        if (resultList.size() > 1) throw new RuntimeException("what the hleyyl ovo se nije smelo dogodit"); //TODO enforce unique in db

        var user = resultList.getFirst();
        if (authentication(user, password)) repository.setActiveUser(user);
    }

    public void logout() {
        repository.setActiveUser(null); //TODO: nuh uh vjerovatno ce bum zbog nullable u optional
    }

    //TODO implement this class
    public boolean authorization(AuthorizedTab tab) {
        var premisionLevelOfActiveUser = switch (repository.getActiveUser().get()) {
            case Admin _ -> 0;
            case Guest _ -> 5;
            case null, default -> 10_000;
        };
        return tab.getAllowedPremisionLevel >= premisionLevelOfActiveUser;
    }
}
