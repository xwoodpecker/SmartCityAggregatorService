package htw.smartcity.aggregator.average;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AverageRepository extends JpaRepository<Average, Long>
{
    Page<Average> findAverageBySensorId(Long id, Pageable pageable);
}
