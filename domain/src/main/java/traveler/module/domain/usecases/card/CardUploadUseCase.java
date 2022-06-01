package traveler.module.domain.usecases.card;

import traveler.module.domain.repository.CardDomainRepository;

public class CardUploadUseCase {

    private final CardDomainRepository repository;

    public CardUploadUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void upload(){
        this.repository.upload();
    }
}