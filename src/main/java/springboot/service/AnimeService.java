/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springboot.domain.Anime;
import springboot.exeption.BadRequestException;
import springboot.mapper.AnimeMapper;
import springboot.repository.AnimeRepository;
import springboot.requests.AnimePostRequestBody;
import springboot.requests.AnimePutRequestBody;

/**
 *
 * @author uilson
 */
@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);

    }

    public Anime findByIdOrThrowBadRequest(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException( "Anime not found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        Anime anime = animeMapper.toAnime(animePostRequestBody);
        return animeRepository.save(anime);
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequest(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime animeSaved = findByIdOrThrowBadRequest(animePutRequestBody.getId());
        Anime anime = animeMapper.toAnime(animePutRequestBody);
        anime.setId(animeSaved.getId());
        animeRepository.save(anime);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);

    }
}
