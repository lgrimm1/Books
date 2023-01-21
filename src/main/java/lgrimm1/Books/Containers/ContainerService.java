package lgrimm1.Books.Containers;

import lgrimm1.Books.Books.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ContainerService {

	private final ContainerRepository containerRepository;
	private final BookRepository bookRepository;

	@Autowired
	public ContainerService(ContainerRepository containerRepository, BookRepository bookRepository) {
		this.containerRepository = containerRepository;
		this.bookRepository = bookRepository;
	}

	public ContainerEntity createNewEntity(ContainerEntity newContainerEntity) {
		if (containerRepository.findByName(newContainerEntity.getName()).isPresent()) {
			return null;
		}
		return containerRepository.save(newContainerEntity);
	}

	public List<ContainerEntity> getAllEntities() {
		return containerRepository.findAll();
	}

	public ContainerEntity getEntityById(long id) {
		Optional<ContainerEntity> optionalContainerEntity = containerRepository.findById(id);
		return optionalContainerEntity.orElse(null);
	}

	public ContainerEntity updateEntity(ContainerEntity modifiedContainerEntity) {
		long id = modifiedContainerEntity.getId();
		if (id == 0) {
			return null;
		}
		if (!containerRepository.existsById(id)) {
			return null;
		}
		Optional<ContainerEntity> containerEntityWithIdenticalName = containerRepository.findByName(modifiedContainerEntity.getName());
		if (containerEntityWithIdenticalName.isPresent() && containerEntityWithIdenticalName.get().getId() != id) {
			return null;
		}
		ContainerEntity oldContainerEntity = containerRepository.getReferenceById(id);
		oldContainerEntity.setName(modifiedContainerEntity.getName());
		oldContainerEntity.setRemarks(modifiedContainerEntity.getRemarks());
		return oldContainerEntity;
	}

	public boolean deleteEntityById(long id) {
		if (id == 0) {
			return false;
		}
		if (!containerRepository.existsById(id)) {
			return false;
		}
		if (bookRepository.findByContainerId(id).isPresent()) {
			return false;
		}
		containerRepository.deleteById(id);
		return true;
	}

}
