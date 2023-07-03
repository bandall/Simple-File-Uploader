package hello.test.domain.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FileMetaRepositoryJdbcTemplate implements FileMetaRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FileMetaRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public FileMetadata save(FileMetadata fileMetadata) {
        String sql = "insert into file(stored_file_name, original_file_name, owner_id, file_size) values(:storedFileName, :originalFileName, :ownerId, :fileSize)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(fileMetadata);
        jdbcTemplate.update(sql, params);
        return fileMetadata;
    }

    @Override
    public void deleteByStoredFileName(String storedFileName) {
        String sql = "delete from file where stored_file_name=:storedFileName";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("storedFileName", storedFileName);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<FileMetadata> findOneByStoredFileName(String storedFileName) {
        String sql = "select * from file where stored_file_name=:storedFileName";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("storedFileName", storedFileName);
        try {
            FileMetadata fileMetadata = jdbcTemplate.queryForObject(sql, params, fileMetadataRowMapper());
            return Optional.of(fileMetadata);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FileMetadata> findAllByOriginalFileName(String queryName) {
        String sql = "select * from file where original_file_name like concat('%',:queryName,'%') order by stored_file_name DESC";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("queryName", queryName);
        return jdbcTemplate.query(sql, params, fileMetadataRowMapper());
    }

    @Override
    public List<FileMetadata> findAllByOriginalFileNameOrStoredFileName(String queryName) {
        String sql = "select * from file where original_file_name like concat('%',:queryName,'%') or stored_file_name like concat('%', :queryName,'%') " +
                "order by stored_file_name DESC";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("queryName", queryName);
        return jdbcTemplate.query(sql, params, fileMetadataRowMapper());
    }

    /**
     * sorted by timestamp in file name
     * @return
     */
    @Override
    public List<FileMetadata> findAll() {
        String sql = "select * from file order by stored_file_name DESC";
        return jdbcTemplate.query(sql, fileMetadataRowMapper());
    }

    @Override
    public int countFile() {
        String sql = "select count(*) from file";
        int count;
        try {
            count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long countTotalFileSize() {
        String sql = "select sum(file_size) from file";
        long count;
        try {
            count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    private RowMapper<FileMetadata> fileMetadataRowMapper() {
        return BeanPropertyRowMapper.newInstance(FileMetadata.class);
    }
}
