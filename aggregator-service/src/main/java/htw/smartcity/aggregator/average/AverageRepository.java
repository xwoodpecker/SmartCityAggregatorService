package htw.smartcity.aggregator.average;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Average repository.
 */
@Repository
public interface AverageRepository extends JpaRepository<Average, Long>
{
    /**
     * Find average by sensor id page.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the page
     */
    Page<Average> findAverageBySensorId(Long id, Pageable pageable);
}
