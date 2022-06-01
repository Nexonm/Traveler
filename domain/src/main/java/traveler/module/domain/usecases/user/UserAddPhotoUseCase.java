package traveler.module.domain.usecases.user;

import traveler.module.domain.repository.UserDomainRepository;

public class UserAddPhotoUseCase {

    private final UserDomainRepository repository;

    public UserAddPhotoUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void addPhoto(String path){
        repository.addPhoto(path);
    }
}
