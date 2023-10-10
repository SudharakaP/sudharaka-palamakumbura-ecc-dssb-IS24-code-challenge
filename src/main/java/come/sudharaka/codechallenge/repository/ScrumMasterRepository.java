package come.sudharaka.codechallenge.repository;

import come.sudharaka.codechallenge.domain.ScrumMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScrumMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScrumMasterRepository extends JpaRepository<ScrumMaster, Long> {}
