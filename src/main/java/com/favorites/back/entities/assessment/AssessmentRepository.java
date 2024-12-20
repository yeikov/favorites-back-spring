package com.favorites.back.entities.assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.viewer.Viewer;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

	public List<Assessment> findAllByViewer(Viewer viewer);

	public Page<Assessment> findAllByViewer(Viewer viewer, Pageable pageable);

	public Page<Assessment> findAllByRegistry(Registry registry, Pageable pageable);

	public Iterable<Assessment> findAllByViewerAndRegistry(Viewer viewer, Registry registry);

	@SuppressWarnings("null")
	public Optional<Assessment> findById(Long id);

	public List<Assessment> findAllByRegistryMedia(String media);

	@Query(value = "SELECT a.* FROM assessment AS a INNER JOIN registry AS r ON a.registry_id=r.id AND r.media=:media ORDER BY a.favorite DESC LIMIT 8;", nativeQuery = true)
	public List<Assessment> findM(String media);

	@Query(value = "SELECT a.* FROM assessment AS a INNER JOIN registry AS r ON a.registry_id=r.id AND a.viewer_id=:viewerId AND r.media=:media ORDER BY a.favorite DESC;", nativeQuery = true)
	public List<Assessment> findUM(Long viewerId, String media);
}
