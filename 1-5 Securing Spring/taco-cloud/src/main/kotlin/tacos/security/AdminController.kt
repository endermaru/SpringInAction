package tacos.security

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminService,
) {

    @PostMapping("/deleteOrders")
    fun deleteAllOrders(): String {
        adminService.deleteAllOrders()
        return "redirect:/admin"
    }
}