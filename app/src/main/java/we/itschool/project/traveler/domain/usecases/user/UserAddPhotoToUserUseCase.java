package we.itschool.project.traveler.domain.usecases.user;

import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserAddPhotoToUserUseCase {

    private final UserDomainRepository repository;

    public UserAddPhotoToUserUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void addPhotoToUser(String pathToPhoto){
        repository.addPhotoToUser(pathToPhoto);
    }
}
