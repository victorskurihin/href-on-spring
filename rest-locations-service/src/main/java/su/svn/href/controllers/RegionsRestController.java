package su.svn.href.controllers;

import io.r2dbc.postgresql.PostgresqlServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.href.dao.RegionDao;
import su.svn.href.exceptions.BadValueForRegionIdException;
import su.svn.href.exceptions.RegionDontSavedException;
import su.svn.href.exceptions.RegionNotFoundException;
import su.svn.href.models.Region;
import su.svn.href.models.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static su.svn.href.controllers.Constants.REST_ALL;
import static su.svn.href.controllers.Constants.REST_API;
import static su.svn.href.controllers.Constants.REST_V1_REGIONS;

@RestController()
@RequestMapping(value = REST_API + REST_V1_REGIONS)
public class RegionsRestController
{
    private RegionDao regionDao;

    @Autowired
    public RegionsRestController(RegionDao regionDao)
    {
        this.regionDao = regionDao;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<? extends Answer> createRegion(@RequestBody Region region,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
    {
        if ( ! Objects.isNull(region.getId())) throw new BadValueForRegionIdException();

        return regionDao
            .save(region)
            .map(r -> new AnswerCreated(response, request.getRequestURI(), r.getId()))
            .switchIfEmpty(Mono.error(new RegionDontSavedException()));
    }

    @GetMapping(REST_ALL)
    public Flux<Region> readRegions()
    {
        return regionDao.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Region> readRegionById(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForRegionIdException();

        return regionDao
            .findById(id)
            .switchIfEmpty(Mono.error(new RegionNotFoundException()));
    }

    @PutMapping
    public Mono<? extends Answer> updateRegion(@RequestBody Region region)
    {
        if (Objects.isNull(region) || Objects.isNull(region.getId()) || region.getId() < 1) {
            throw new BadValueForRegionIdException();
        }

        return regionDao
            .save(region)
            .map(r -> new AnswerOk())
            .switchIfEmpty(Mono.error(new RegionDontSavedException()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<? extends Answer> deleteRegion(@PathVariable Long id)
    {
        if (Objects.isNull(id) || id < 1) throw new BadValueForRegionIdException();
        AnswerNoContent answerNoContent = new AnswerNoContent("remove successfully");

        return regionDao
            .findById(id)
            .flatMap(region -> regionDao
                .delete(region)
                .map(v -> answerNoContent)
                .switchIfEmpty(Mono.just(answerNoContent)))
            .switchIfEmpty(Mono.error(new RegionNotFoundException()));
    }

    @ExceptionHandler(BadValueForRegionIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForRegionIdException e)
    {
        return new AnswerBadRequest("Bad value for Region Id");
    }

    @ExceptionHandler(RegionNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(RegionNotFoundException e)
    {
        return new AnswerBadRequest("Region not found for Id");
    }

    @ExceptionHandler(PostgresqlServerErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(PostgresqlServerErrorException e)
    {
        return new AnswerBadRequest("Bad value for Region");
    }

    @ExceptionHandler(RegionDontSavedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(RegionDontSavedException e)
    {
        return new AnswerBadRequest("Region don't saved");
    }
}
