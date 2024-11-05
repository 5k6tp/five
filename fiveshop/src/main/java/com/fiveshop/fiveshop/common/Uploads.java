    package com.fiveshop.fiveshop.common;


    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.io.IOException;

    @RestController
    @RequestMapping("/api/upload")
    public class Uploads {

        // 设置上传目录（确保这个路径存在，并且应用程序有权限写入）
        private static final String UPLOAD_DIR = "C:\\Users\\asus\\Desktop\\javaweb\\fiveshop\\uploads"; // 根据需要调整路径

        @PostMapping("/image")
        public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return Result.fail("上传失败，文件为空");
            }

            try {
                // 获取原始文件名
                String originalFilename = file.getOriginalFilename();
                // 创建新的文件名，防止文件名冲突
                String fileName = System.currentTimeMillis() + "_" + originalFilename;
                // 创建目标文件对象
                File dest = new File(UPLOAD_DIR + File.separator + fileName);
                
                // 检查上传目录是否存在
                if (!dest.getParentFile().exists()) {
                    return Result.fail("上船目錄不存在");
                }

                // 将文件保存到指定位置
                file.transferTo(dest);

                // 返回 JSON 格式的响应，包含上传后图片的访问 URL
                String jsonResponse = "http://localhost:8081/uploads/" + fileName; // Adjust URL if needed
                return Result.success(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.fail(e.getMessage());
        }
    }
    }