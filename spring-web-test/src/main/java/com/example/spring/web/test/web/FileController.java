package com.example.spring.web.test.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.spring.database.test.enums.file.FileModuleEnum;
import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;
import com.example.spring.web.test.dto.FileSaveDTO;
import com.example.spring.web.test.service.IFileService;
import com.example.spring.web.test.vo.BaseUploadVO;
import com.example.spring.web.test.vo.response.FileResVO;

/**
 * @author huss
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public Result upload(BaseUploadVO baseUploadVo) throws IOException {
        InputStream inputStream = baseUploadVo.getFile().getInputStream();
        String fileName = baseUploadVo.getFile().getOriginalFilename();
        FileSaveDTO fileSaveVo = new FileSaveDTO(inputStream, fileName, baseUploadVo.getRemark());
        FileResVO fileResVo = fileService.saveFile(fileSaveVo);
        return new Results().success(fileResVo);
    }

    @GetMapping("/{fileModule}/{infoId}")
    public Result getOne(@PathVariable FileModuleEnum fileModule, @PathVariable Long infoId) {
        Optional<FileResVO> fileOpt = fileService.findByFileModuleAndInfoId(fileModule, infoId);
        if (fileOpt.isPresent()) {
            return new Results().success(fileOpt.get());
        }
        return new Results().success();

    }

}
